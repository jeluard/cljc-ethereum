(ns ethereum.clients.infura
  "https://infura.io/docs/ethereum/wss/introduction"
  (:require [ethereum.core            :as ethereum]
            [ethereum.transports.http :as http]
            [ethereum.utils.json-rpc  :as json-rpc]))

(defn- http-subdomain [network]
  (case network
    1 "mainnet"))

(defn- http-url [network infura-id]
  (str "https://" (http-subdomain network) ".infura.io/v3/" infura-id))

(defn create
  ""
  [{:keys [network infura-id]}]
  (let [url (http-url network infura-id)]
    (reify ethereum/Client
      (capacities [_])
      (send [_ {:keys [method params]} cb]
        (http/http-post url (json-rpc/request (json-rpc/next-increment) method params) cb)))))
