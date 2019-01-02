(ns ethereum.utils.json-test
    (:require [clojure.test :refer [is deftest testing]]
              [ethereum.utils.json :as json]))

(deftest map->json
  (is (= "null"          (json/map->json nil)))
  (is (= "{}"            (json/map->json {})))
  (is (= "{\"a\":[1,2]}" (json/map->json {:a [1 2]}))))

(deftest json->map
  (is (= nil         (json/json->map nil)))
  (is (= nil         (json/json->map "null")))
  (is (= {}          (json/json->map "{}")))
  (is (= {"a" [1 2]} (json/json->map "{\"a\":[1,2]}"))))