
[![CircleCI](https://circleci.com/gh/jeluard/cljc-ethereum.svg?style=svg)](https://circleci.com/gh/jeluard/cljc-ethereum)

[![cljdoc badge](https://cljdoc.org/badge/jeluard/cljc-ethereum)](https://cljdoc.org/d/jeluard/cljc-ethereum/CURRENT)

A high-level Clojure(Script) ethereum JSON-RPC client

## Usage

```clojure
(ns ...
  (:require [ethereum.clients.infura :as infura]
            [ethereum.core           :as ethereum]
            [ethereum.net            :as net]))

(let [client (infura/create {:network 1 :infura-id "..."})]
    (ethereum/request client (net/version) println))
```

## Development

Development requires [Deps and CLI](https://clojure.org/guides/getting_started) tooling installed.

Run examples using `make dev`.

### Tests 

Run all tests using `make tests`

## License

`EPL-2.0` see `LICENSE`