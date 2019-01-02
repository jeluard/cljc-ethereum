(ns ethereum.utils.json-rpc
  "Helper methods to help with the [JSON-RPC](https://www.jsonrpc.org/specification) standard"
  (:refer-clojure :exclude [next]))

(defprotocol IdGenerator
  (next [_] ""))

(def AutoIncrementIdGenerator
  (let [id (atom 0)]
    (reify IdGenerator
      (next [_] (swap! id inc)))))

(defn next-increment [] (next AutoIncrementIdGenerator))

(defn request
  ([id method] (request id method nil))
  ([id method params] ;; params: either vector or map. method: string
   (merge {:jsonrpc "2.0"
           :id      id
           :method  method}
          (when params
            {:params params}))))

(defn notification?
  "Returns true if provided request is a notification"
  [m]
  (not (contains? m :id)))

(defn error?
  "Returns true if provided response is an error"
  [m]
  (not (contains? m :error)))

;; code, message, data