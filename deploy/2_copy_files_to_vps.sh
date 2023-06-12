# 144.24.2.220 ssh-key-2023-06-12.key

remote_ip_vps=$1
private_key_ssh=$2

cd server

scp -i ../$private_key_ssh Reaktor-0.0.1-SNAPSHOT-jar-with-dependencies.jar opc@$remote_ip_vps:/home/opc/Reaktor/server
scp -i ../$private_key_ssh Dockerfile opc@$remote_ip_vps:/home/opc/Reaktor/server

cd ../database

scp -i ../$private_key_ssh Dockerfile opc@$remote_ip_vps:/home/opc/Reaktor/database

cd ../proxy_and_web

scp -i ../$private_key_ssh nginx.conf opc@$remote_ip_vps:/home/opc/Reaktor/proxy_and_web
scp -i ../$private_key_ssh reaktorweb.zip opc@$remote_ip_vps:/home/opc/Reaktor/proxy_and_web
scp -i ../$private_key_ssh Dockerfile opc@$remote_ip_vps:/home/opc/Reaktor/proxy_and_web
