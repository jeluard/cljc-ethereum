(ns ethereum.net
  "Helper functions for `net_*` JSON_RPC methods"
  (:require [ethereum.core :as ethereum])
  #?(:cljs (:require-macros [ethereum.core :as ethereum])))

(ethereum/defrequest version
  {:method "net_version"
   :output :data})

(ethereum/defrequest listening?
  {:method "net_listening"
   :output :boolean})

(ethereum/defrequest peers
  {:method "net_peerCount"
   :output :quantity})