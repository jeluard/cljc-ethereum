(ns ethereum.eth
  "Helper functions for `eth_*` JSON_RPC methods"
  (:require [ethereum.core        :as ethereum]
            [ethereum.utils.codec :as codec]
            [ethereum.utils.json  :as json])
  #?(:cljs (:require-macros [ethereum.core :as ethereum])))

(ethereum/defrequest protocol-version
  {:method "eth_protocolVersion"
   :output :data})

(defmethod ethereum/unmarshall :syncing-data [_ value]
  (cond
    (false? value) value
    (string? value)
    (let [{:strs [startingBlock currentBlock highestBlock]} (json/json->map value)]
      {:starting startingBlock
       :current  currentBlock
       :highest  highestBlock})))

(ethereum/defrequest syncing?
  {:method "eth_syncing"
   :output :syncing-data})

(ethereum/defrequest coinbase
  {:method "eth_coinbase"
   :output :address})

(ethereum/defrequest mining?
  {:method "eth_mining"
   :output :boolean})

(ethereum/defrequest hashrate
  {:method "eth_hashrate"
   :output :quantity})

(ethereum/defrequest gas-price
  {:method "eth_gasPrice"
   :output :quantity})

(ethereum/defrequest accounts
  {:method "eth_accounts"
   :output [:address]})

(ethereum/defrequest block-number
  {:method "eth_blockNumber"
   :output :quantity})

(defmethod ethereum/marshall :block-id [_ value]
  (cond
    (number? value) (codec/hex->quantity value)
    (#{:earliest :latest :pending} value) (str value)))

(ethereum/defrequest balance
  {:method "eth_blockNumber"
   :inputs [:address :block-id]
   :output :quantity})

(ethereum/defrequest storage-at
  {:method "eth_getStorageAt"
   :inputs [:address :quantity :block-id]
   :output :data})

(ethereum/defrequest transaction-count
  {:method "eth_getTransactionCount"
   :inputs [:address :block-id]
   :output :quantity})

(ethereum/defrequest block-transaction-count-by-hash
  {:method "eth_getBlockTransactionCountByHash"
   :inputs [:hash]
   :output :quantity})

(ethereum/defrequest block-transaction-count-by-number
  {:method "eth_getBlockTransactionCountByNumber"
   :inputs [:block-id]
   :output :quantity})

(ethereum/defrequest uncle-count-by-block-hash
  {:method "eth_getUncleCountByBlockHash"
   :inputs [:hash]
   :output :quantity})

(ethereum/defrequest uncle-count-by-number
  {:method "eth_getUncleCountByBlockNumber"
   :inputs [:block-id]
   :output :quantity})

(ethereum/defrequest code
  {:method "eth_getCode"
   :inputs [:address :block-id]
   :output :data})

(ethereum/defrequest sign
  {:method "eth_sign"
   :inputs [:address :data]
   :output :data})

;; https://docs.pantheon.pegasys.tech/en/latest/Reference/JSON-RPC-API-Objects/#transaction-object

(ethereum/defrequest send-transaction
  {:method "eth_sendTransaction"
   :inputs [:transaction]
   :output :hash})

(ethereum/defrequest send-raw-transaction
  {:method "eth_sendRawTransaction"
   :inputs [:data]
   :output :hash})

;; https://docs.pantheon.pegasys.tech/en/latest/Reference/JSON-RPC-API-Objects/#block-object
;; https://docs.pantheon.pegasys.tech/en/latest/Reference/JSON-RPC-API-Objects/#transaction-call-object

(ethereum/defrequest call
  {:method "eth_call"
   :inputs [:transaction-call :block]
   :output :data})

(ethereum/defrequest estimate-gas
  {:method "eth_estimateGas"
   :inputs [:transaction-call :block]
   :output :quantity})

(ethereum/defrequest block-by-hash
  {:method "eth_blockByHash"
   :inputs [:hash :boolean]
   :output :block})

(ethereum/defrequest block-by-number
  {:method "eth_blockByNumber"
   :inputs [:block-id :boolean]
   :output :block})

(ethereum/defrequest transaction-by-hash
  {:method "eth_transactionByHash"
   :inputs [:hash]
   :output :transaction})

(ethereum/defrequest transaction-by-hash-and-index
  {:method "eth_transactionByBlockHashAndIndex"
   :inputs [:hash :quantity]
   :output :transaction})

(ethereum/defrequest transaction-by-block-and-index
  {:method "eth_transactionByBlockNumberAndIndex"
   :inputs [:block-id :quantity]
   :output :transaction})

;; https://docs.pantheon.pegasys.tech/en/latest/Reference/JSON-RPC-API-Objects/#transaction-receipt-object

(ethereum/defrequest transaction-receipt
  {:method "eth_transactionReceipt"
   :inputs [:hash]
   :output :transaction-receipt})

(ethereum/defrequest uncle-by-hash-and-index
  {:method "eth_uncleByBlockHashAndIndex"
   :inputs [:hash :quantity]
   :output :transaction})

(ethereum/defrequest uncle-by-block-and-index
  {:method "eth_uncleByBlockNumberAndIndex"
   :inputs [:block-id :quantity]
   :output :transaction})

(defmethod ethereum/marshall :filter-options [_ {:keys [from to address topics]}]
  "See https://docs.pantheon.pegasys.tech/en/latest/Reference/JSON-RPC-API-Objects/#filter-options-object"
  (json/map->json
    (merge
      (when from {:fromBlock (ethereum/marshall :block-id from)})
      (when to {:toBlock (ethereum/marshall :block-id to)})
      (when address {:address address})
      (when topics {:topics topics}))))

(ethereum/defrequest new-filter
  {:method "eth_newFilter"
   :inputs [:filter-options]
   :output :quantity})

(ethereum/defrequest new-block-filter
  {:method "eth_newBlockFilter"
   :output :quantity})

(ethereum/defrequest new-pending-transaction-filter
  {:method "eth_newPendingTransactionFilter"
   :output :quantity})

(ethereum/defrequest uninstall-filter
  {:method "eth_uninstallFilter"
   :inputs [:quantity]
   :output :boolean})

;; https://docs.pantheon.pegasys.tech/en/latest/Reference/JSON-RPC-API-Objects/#log-object

(ethereum/defrequest filter-changes
  {:method "eth_getFilterChanges"
   :inputs [:quantity]
   :output [:log]})

(ethereum/defrequest filter-logs
  {:method "eth_getFilterLogs"
   :inputs [:quantity]
   :output [:log]})

(ethereum/defrequest logs
  {:method "eth_getLogs"
   :inputs [:filter-options]
   :output [:log]})
