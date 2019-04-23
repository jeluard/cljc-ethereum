(ns ethereum.core
  "Helper functions for `eth_*` JSON_RPC methods"
  (:refer-clojure :exclude [send])
  (:require [ethereum.utils.codec :as codec]))

(defprotocol Client
  "A JSON-RPC client implementation"
  (capacities [_])
  (send [_ request callback]))

;; TODO generic HTTP, IPC and WS clients
;; https://github.com/ethereum/EIPs/blob/master/EIPS/eip-1193.md

(defprotocol Signer
  "A signer implementation"
  (sign [_ data]))

;; Sign
;; https://github.com/ethers-io/ethers.js/issues/298
;; https://github.com/MetaMask/metamask-extension/issues/3475
;; https://github.com/paritytech/parity-ethereum/issues/2265
;; https://eips.ethereum.org/EIPS/eip-191
;; https://eips.ethereum.org/EIPS/eip-712
;; https://hackernoon.com/a-closer-look-at-ethereum-signatures-5784c14abecc
;; https://medium.com/metamask/scaling-web3-with-signtypeddata-91d6efc8b290

(defmulti marshall
  "Clojure data to JSON-RPC"
  (fn [type value] type))

(defmethod marshall :data [_ value] value)

(defmethod marshall :boolean [_ value] value)

(defmethod marshall :quantity [_ value] (codec/quantity->hex value))

(defmethod marshall :address [_ value] value)

(defmethod marshall :hash [_ value] value)

(defmethod marshall :default [type value] (throw (str "Don't know how to marshall" type "::" value)))

(defmulti unmarshall
  "JSON-RPC data to Clojure"
  (fn [type value]
    (cond
      (keyword? type) type
      (vector? type)  ::vector)))

(defmethod unmarshall ::vector [[first] value] (mapv #(unmarshall first %) value))

(defmethod unmarshall :data [_ value] (when (string? value) value))

(defmethod unmarshall :address [_ value] (when (string? value) value))

(defmethod unmarshall :hash [_ value] (when (string? value) value))

(defmethod unmarshall :boolean [_ value]
  (cond
    (boolean? value) value
    (string? value)
    (case value
      "true"  true
      "false" false
      nil)))

(defmethod unmarshall :quantity [_ value] (when (string? value) (codec/hex->quantity value)))

(defmethod unmarshall :default [type value] (throw (str "Don't know how to unmarshall" type "::" value)))

(defn- request-callback [cb type value]
  (let [{:keys [result error]} value]
    (if result
      (cb {:result (unmarshall type result)})
      (cb {:error error}))))

(defn request
  [client {:keys [output] :as m} cb]
  (send client (dissoc m :output) #(request-callback cb output %)))

(defrecord Request [method inputs output]
  #?(:clj  clojure.lang.IFn
     :cljs IFn)
   (#?(:clj invoke :cljs -invoke) [_]
    {:method method
     :output output})
   (#?(:clj invoke :cljs -invoke) [_ first]
     {:method method
      :params [(marshall (first inputs) first)]
      :output output}))

#?(:clj
   (defmacro defrequest [name {:keys [method] :as m}]
     `(def ~(with-meta name {:ethereum/doc (str "https://github.com/ethereum/wiki/wiki/JSON-RPC#" method)})
        (map->Request ~m))))
