./scripts/build -- src/cond.js src/head.js src/tail.js src/map.js src/pipe.js src/isEmpty.js src/T.js src/identity.js src/reduce.js src/chain.js src/split.js src/nth.js src/fromPairs.js src/toPairs.js src/nthArg.js src/prop.js src/ifElse.js src/take.js src/equals.js src/zipObj.js src/drop.js > dist/ramda.route.js


uglifyjs ./lib/ramda.route.js ./lib/jueji.js ./js/route.js -o ./dist/route.all.min.js