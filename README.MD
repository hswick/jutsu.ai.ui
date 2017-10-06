# jutsu.ai.ui

Training visualization plugin for jutsu.ai. It is functionally a wrapper for deeplearning4j-ui that is meant to be trivial to use.
https://deeplearning4j.org/visualization

# Usage

Add jutsu.ai.ui to your dependencies

```clojure
[hswick/jutsu.ai.ui "0.0.1"]
```

Then require it in your namespace:

```clojure
[jutsu.ai.ui.core :as ui]
```

Then when you are initializing your network. Instead of using the defualt initialize-net! provided by jutsu.ai, you can initialize it with a UI.

```clojure
(ns cool-codes.core
  (:require [jutsu.ai.ui.core :as ui]
            [jutsu.ai.core :as ai]))

(def iris-net-config [:layers  [[:dense [:n-in 4 :n-out 10 :activation :relu]]
                                [:dense [:n-in 10 :n-out 10 :activation :relu]]
                                [:output :negative-log-likelihood [:n-in 10 :n-out 3
                                                                   :activation :softmax]]]])

(def iris-net (-> iris-net-config
                  ai/network
                  ui/initialize-with-ui))
```

Once you kick off the training a server will be started up, which you can access locally at `http://localhost:9000/train` with your browser.

Recommended usage is to use something like boot's wait command when running your code. I like to include a `run` task to my build.boot to execute my code. So if you are using the ui when training type something like this into the command line:

```
boot wait running
```

# Dev

Run `boot night` to startup nightlight and begin editing your project in a browser.

`boot wait test-code` then type "http://localhost:9000/train" into your browser.

## License

Copyright © 2017 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
