(ns ethereum.web3
  "Helper functions for `web3_*` JSON_RPC methods"
  (:require [ethereum.core :as ethereum])
  #?(:cljs (:require-macros [ethereum.core :as ethereum])))

(ethereum/defrequest client-version
  {:method "web3_clientVersion"
   :output :data})

(ethereum/defrequest sha3
  {:method "web3_sha3"
   :inputs [:data]
   :output :data})