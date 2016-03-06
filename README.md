# Ring-Cljsjs

[![Clojars Project](https://img.shields.io/clojars/v/ring-cljsjs.svg)](https://clojars.org/ring-cljsjs)

Ring middleware to serve static assets from [cljsjs][].

[cljsjs]: http://www.webjars.org/

## Installation

Include the following dependency in your project dependencies:

```
[ring-cljsjs "0.1.0"]
```

## Usage

Require the middleware and add it to your handler.

```clj
(require '[ring.middleware.cljsjs :refer [wrap-cljsjs]])

(def app (wrap-cljsjs handler)
```

Cljsjs assets will then be served from the following path:

```
/cljsjs/<package>/<asset path>
```

For example, if you include the [cljsjs/react-mdl "1.4.3-0"] dependency, then the minified material CSS will be available at:

```
/cljsjs/react-mdl/material.min.css
```

By default assets are placed on the `/cljsjs` path. You can change
the path by specifying prefix in a options map.

```clj
(def app (web-cljsjs handler {:prefix "/cljsjs"}))
```

## License

Copyright © 2016 Juho Teperi

Based on [ring-webjars](https://github.com/weavejester/ring-webjars)<br>
Copyright © 2015 James Reeves

Released under the MIT license.
