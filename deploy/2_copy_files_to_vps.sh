remote_ip_vps=$1
private_key_ssh=$2

cd server

scp -i ../$private_key_ssh Reaktor-0.0.1-SNAPSHOT-jar-with-dependencies.jar ubuntu@$remote_ip_vps:/home/ubuntu/Reaktor/server
scp -i ../$private_key_ssh Dockerfile ubuntu@$remote_ip_vps:/home/ubuntu/Reaktor/server

cd ../database

scp -i ../$private_key_ssh Dockerfile ubuntu@$remote_ip_vps:/home/ubuntu/Reaktor/database

cd ../proxy_and_web

scp -i ../$private_key_ssh nginx.conf ubuntu@$remote_ip_vps:/home/ubuntu/Reaktor/proxy_and_web
scp -i ../$private_key_ssh reaktorweb.zip ubuntu@$remote_ip_vps:/home/ubuntu/Reaktor/proxy_and_web
scp -i ../$private_key_ssh Dockerfile ubuntu@$remote_ip_vps:/home/ubuntu/Reaktor/proxy_and_web
