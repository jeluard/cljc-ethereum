(ns ethereum.clients.geth
  "
  https://github.com/ethereum/wiki/wiki/JSON-RPC
  https://github.com/ethereum/go-ethereum/wiki/Management-APIs
  https://github.com/ethereum/go-ethereum/wiki/RPC-PUB-SUB"
  (:require [ethereum.core            :as ethereum]
            [ethereum.transports.http :as http]
            [ethereum.utils.json-rpc  :as json-rpc]))

(def default-port 8545)