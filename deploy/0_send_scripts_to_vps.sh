# 144.24.2.220 ssh-key-2023-06-12.key
remote_ip_vps=$1
private_key_ssh=$2

scp -i $private_key_ssh 1_init_vps.sh opc@$remote_ip_vps:/home/opc/
scp -i $private_key_ssh 3_build_and_run_dockers.sh opc@$remote_ip_vps:/home/opc/

# ssh -i ssh-key-2023-06-12.key opc@144.24.2.220