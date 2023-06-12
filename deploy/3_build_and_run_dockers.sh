
cd 
cd Reaktor/server
sudo docker build -t reaktor -f Dockerfile .

cd ../database
sudo docker build -t reaktor-mysql -f Dockerfile .

cd ../proxy_and_web

sudo apt-get install unzip
unzip reaktorweb.zip
rm reaktorweb.zip

sudo docker build -t reaktor-nginx -f Dockerfile .

sudo docker run -d -p 3306:3306 --network=host --name reaktor-mysql reaktor-mysql
sudo docker run -d -p 80:80 --network=host --name reaktor-nginx reaktor-nginx

sleep 10
sudo docker run -d -p 8084:8084 --network=host --name reaktor reaktor