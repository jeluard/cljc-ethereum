(ns ethereum.transports.http
  (:require [ethereum.utils.json :as json]))

#?(:cljs
   (defn- result [o]
     (let [{:strs [result error]} (js->clj o)]
       (merge
         (when result {:result result})
         (when error {:result error})))))

(defn- error [s]
  {:error s})

(defn- http-post [url request cb]
  #?(:cljs
     (.then
       (js/fetch url (clj->js {:method  "POST"
                               :body    (json/map->json request)
                               :headers {"Content-Type" "application/json"}}))
       (fn [response]
         (if (.-ok response)
           (.then (.json response)
             #(cb (result %))
             #(cb (error "aa")))
           (cb (error "ee"))))
       (fn [o] (cb (error ".."))))))