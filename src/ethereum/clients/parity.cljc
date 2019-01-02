(ns ethereum.clients.parity
  "https://wiki.parity.io/JSONRPC"
  (:require [ethereum.core            :as ethereum]
            [ethereum.transports.http :as http]
            [ethereum.utils.json-rpc  :as json-rpc]))

(def default-http-port 8545)
(def default-websocket-port 8546)