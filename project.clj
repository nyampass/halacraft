(defproject halacraft "0.1.5-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [nrepl/nrepl "0.7.0"]]
  :resource-paths ["resources/plugin.yml"
                   "resources/spigot-api-1.15.2-R0.1-SNAPSHOT-shaded.jar"]
  
  :uberjar-exclusions [#"spigot-api-.+\.jar"]

  :java-source-paths ["java"]
  :repl-options {:init-ns halacraft.core}
  :profiles {:uberjar {:aot :all}})
