sudo dnf install -y ansible
echo "localhost ansible_connection=local" > /tmp/local_node_inventory
ansible-pull --url=https://github.com/prde/prde-install-fedora.git -K -i /tmp/local_node_inventory
