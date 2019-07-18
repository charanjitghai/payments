echo "starting application"
nohup java -jar target/payments-1.0-SNAPSHOT-jar-with-dependencies.jar &
source src/run/set_env.sh
