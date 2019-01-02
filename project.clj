(defproject status-im/cljc-ethereum "iteration-4-7-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]]

  :source-paths ["src"]

  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]
  :cljsbuild {:builds [{:id "tests"
                        :source-paths ["src" "test"]
                        :compiler {:output-to "target/testable.js"
                                   :optimizations :none
                                   :cache-analysis false
                                   :target :nodejs
                                   :main "ethereum.demo"
                                   :pretty-print true}}]})
