(ns ethereum.examples
  (:require [devtools.core           :as devtools]
            [ethereum.clients.infura :as infura]
            [ethereum.core           :as ethereum]
            [ethereum.eth            :as eth]
            [ethereum.net            :as net]))

(enable-console-print!)
(devtools/install!)

(defn run []
  (let [client (infura/create {:network 1 :infura-id "d42c696f84ad4c46a2cab5ecde8941a7"})]
    (ethereum/request client (net/version) println)))