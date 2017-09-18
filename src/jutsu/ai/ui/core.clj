(ns jutsu.ai.ui.core
  (:import [org.deeplearning4j.ui.api UIServer]
           [org.deeplearning4j.ui.storage InMemoryStatsStorage FileStatsStorage]
           [org.deeplearning4j.ui.stats StatsListener]
           [org.deeplearning4j.api.storage.impl RemoteUIStatsStorageRouter]
           [org.deeplearning4j.api.storage StatsStorageRouter]))

(defn ui-server []
  (UIServer/getInstance))

(defn in-memory-stats-storage []
  (InMemoryStatsStorage.))

(defn file-stats-storage [file]
  (FileStatsStorage. file))

(defn stats-listener [stats-storage frequency]
  (StatsListener. stats-storage frequency))

(defn initialize-with-ui
  ([network]
   (initialize-with-ui network (in-memory-stats-storage) 1))
  ([network storage frequency]
   (.init network)
   (.setListeners network (list (stats-listener storage frequency)))
   (.attach (ui-server) storage)
   network))

(defn initialize-with-remote 
  ([network]
   (initialize-with-remote network (RemoteUIStatsStorageRouter. "http://localhost:9000") 1))
  ([network storage frequency]
   (.init network)
   (.setListeners network (list (stats-listener storage frequency)))))


