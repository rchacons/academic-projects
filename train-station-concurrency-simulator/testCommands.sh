printf "\n*** INIT ***\n"

curl http://localhost:8124/trains
curl http://localhost:8124/voyageurs

printf "\n*** ADD TRAINS ***\n"

curl -X POST -H 'Content-type:application/json' -H 'Accept:application/json' -d '{"capacite":100,"vitesse":250}' http://localhost:8124/trains
curl -X POST -H 'Content-type:application/json' -H 'Accept:application/json' -d '{"capacite":80,"vitesse":200}' http://localhost:8124/trains


printf "\n*** GET TRAINS ***\n"

curl http://localhost:8124/trains

printf "\n*** ADD VOYAGEURS ***\n"

curl -X POST -H 'Content-type:application/json' -H 'Accept:application/json' -d '{"name":VoyageurA}' http://localhost:8124/voyageurs
curl -X POST -H 'Content-type:application/json' -H 'Accept:application/json' -d '{"name":VoyageurB}' http://localhost:8124/voyageurs

printf "\n*** GET VOYAGEURS ***\n"

curl http://localhost:8124/voyageurs

