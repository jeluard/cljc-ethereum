(ns ethereum.utils.json
  "JSON utils functions"
  #?(:clj (:require [jsonista.core :as jsonista])))

(defn map->json
  "Converts a map into a JSON string."
  [o]
  #?(:clj  (jsonista/write-value-as-string o)
     :cljs (js/JSON.stringify (clj->js o))))

(defn json->map
  "Converts a JSON string into a map. Keys are strings."
  [o]
  #?(:clj  (jsonista/read-value o)
     :cljs (js->clj (js/JSON.parse o))))