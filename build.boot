(set-env!
  :resource-paths #{"src"}
  :dependencies '[[org.clojure/clojure "1.8.0"]
                  [nightlight "1.7.0" :scope "test"]
                  [adzerk/boot-test "1.2.0" :scope "test"]
                  [org.nd4j/nd4j-api "0.8.0" :scope "test"]
                  [hswick/jutsu.ai "0.0.9"]
                  [org.deeplearning4j/deeplearning4j-ui_2.10 "0.8.0"]]
  :repositories (conj (get-env :repositories)
                      ["clojars" {:url "https://clojars.org/repo"
                                  :username (System/getenv "CLOJARS_USER")
                                  :password (System/getenv "CLOJARS_PASS")}]))

(task-options!
  jar {:main 'jutsu.ai.ui.core
       :manifest {"Description" "Clojure library meant to do..."}}
  pom {:version "0.0.1"
       :project 'author/jutsu.ai.ui
       :description "jutsu.ai.ui is meant to do..."
       :url "https://github.com/author/jutsu.ai.ui"}
  push {:repo "clojars"})

(deftask deploy []
  (comp
    (pom)
    (jar)
    (push)))

;;So nightlight can still open even if there is an error in the core file
(try
  (require 'jutsu.ai.ui.core)
  (catch Exception e (.getMessage e)))

(require
  '[nightlight.boot :refer [nightlight]]
  '[adzerk.boot-test :refer :all])

(deftask night []
  (comp
    (wait)
    (nightlight :port 4000)))

(deftask testing [] (merge-env! :source-paths #{"test"}) identity)

(deftask test-code
  []
  (comp
    (testing)
    (test)))
