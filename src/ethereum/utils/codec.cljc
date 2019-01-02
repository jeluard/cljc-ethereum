(ns ethereum.utils.codec
  "Utils functions to perform ethereum conversions (hex, ...)")

(def ^:const hex-prefix "0x")
(def ^:const zero (str hex-prefix "0"))

(defn- hex->base [s]
  (when (< 2 (count s))
    (when (re-matches (re-pattern (str "^" hex-prefix "[a-zA-Z1-6][a-zA-Z0-6]*")) s)
      (subs s 2))))

(defn- base->hex [s]
  (when (string? s)
    (str hex-prefix s)))

(defn hex->quantity [s]
  (when (string? s)
    (if (= zero s)
      0
      (when-let [^String b (hex->base s)]
        #?(:clj (BigInteger. b 16))))))

;; https://github.com/MikeMcl/bignumber.js/blob/master/bignumber.js
;; https://github.com/ethereumjs/ethereumjs-tx/blob/master/index.js
;; https://github.com/donmccurdy/hex2dec/blob/master/index.js
;; https://github.com/jimm/clojure/blob/master/cryptopals/src/cryptopals/util.clj
;; https://stackoverflow.com/questions/26082594/converting-a-number-from-base-10-to-another-base-in-clojure
;; https://github.com/google/closure-library/blob/master/closure/goog/crypt/basen.js#L55

;; https://docs.ethers.io/ethers.js/html/api-utils.html#signature
(defn quantity->hex [i]
  (when (number? i)
    (if (zero? i)
      zero
      #?(:clj (base->hex (Integer/toString i 16))))))