(ns ethereum.utils.codec-test
    (:require [clojure.test :refer [is deftest testing]]
              [ethereum.utils.codec :as codec]))

(deftest hex->quantity
  (is (= nil (codec/hex->quantity nil)))
  (is (= nil (codec/hex->quantity "0x")))
  (is (= nil (codec/hex->quantity "0x041")))
  (is (= nil (codec/hex->quantity "ff")))
  (is (= 0 (codec/hex->quantity "0x0")))
  (is (= 65 (codec/hex->quantity "0x41")))
  (is (= 1024 (codec/hex->quantity "0x400"))))

(deftest quantity->hex
  (is (= nil (codec/quantity->hex nil)))
  (is (= nil (codec/quantity->hex "0x041")))
  (is (= nil (codec/quantity->hex "ff")))
  (is (= "0x0" (codec/quantity->hex 0)))
  (is (= "0x41" (codec/quantity->hex 65)))
  (is (= "0x400" (codec/quantity->hex 1024))))