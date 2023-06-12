remote_ip_vps=$1
private_key_ssh=$2

scp -i $private_key_ssh 1_init_vps.sh ubuntu@$remote_ip_vps:/home/ubuntu/
scp -i $private_key_ssh 3_build_and_run_dockers.sh ubuntu@$remote_ip_vps:/home/ubuntu/

