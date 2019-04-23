(ns ethereum.utils.codec
  "Utils functions to perform ethereum conversions (hex, ...).
   Defined in https://github.com/ethereum/wiki/wiki/JSON-RPC#hex-value-encoding")

(def ^:const hex-prefix "0x")
(def ^:const hex-prefix-length (count hex-prefix))
(def ^:const zero (str hex-prefix "0"))
(def ^:const base 16)

(defn- hex->base [s]
  (when (< hex-prefix-length (count s))
    (when (re-matches (re-pattern (str "^" hex-prefix "[a-zA-Z1-6][a-zA-Z0-6]*")) s)
      (subs s hex-prefix-length))))

(defn- base->hex [s]
  (when (string? s)
    (str hex-prefix s)))

(defn hex->quantity
  "Converts hex to a QUANTITY"
  [s]
  (when (string? s)
    (if (= zero s)
      0
      (when-let [b (hex->base s)]
        #?(:clj  (BigInteger. ^String b base)
           :cljs (js/parseInt b base))))))

(defn quantity->hex
  "Converts a QUANTITY to hex"
  [i]
  (when (number? i)
    (if (zero? i)
      zero
      (base->hex
        #?(:clj  (Integer/toString i base)
           :cljs (.toString i base))))))