(ns ethereum.ercs.core
  "https://eips.ethereum.org/erc"
  (:refer-clojure :exclude [send]))

;; 20, 721, ens, https://eips.ethereum.org/EIPS/eip-165
;; https://eips.ethereum.org/EIPS/eip-55
;; https://eips.ethereum.org/EIPS/eip-191
;; https://eips.ethereum.org/EIPS/eip-681
;; https://eips.ethereum.org/EIPS/eip-831
;; https://eips.ethereum.org/EIPS/eip-1167

(def chains "Map of chains as defined here: https://github.com/ethereum/EIPs/blob/master/EIPS/eip-155.md#list-of-chain-ids"
  {1 "Ethereum mainnet"
   2 "Ropsten"})