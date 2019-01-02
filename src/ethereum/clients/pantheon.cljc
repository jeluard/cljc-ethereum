(ns ethereum.clients.pantheon
  "
  https://docs.pantheon.pegasys.tech/en/latest/Reference/JSON-RPC-API-Methods/
  https://docs.pantheon.pegasys.tech/en/latest/Using-Pantheon/RPC-PubSub/
  https://docs.pantheon.pegasys.tech/en/latest/Reference/JSON-RPC-API-Objects/"
  (:require [ethereum.core            :as ethereum]
            [ethereum.transports.http :as http]
            [ethereum.utils.json-rpc  :as json-rpc]))

(def default-http-port 8545)
(def default-websocket-port 8546)