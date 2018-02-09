// Step to installa software using ansible playbook.
// To be re-used among different stages and pipelines
def call(String withCredentials_CredentialsId, String playBook) {
    withCredentials([usernamePassword(credentialsId: withCredentials_CredentialsId, passwordVariable: 'vaultpass', usernameVariable: 'vaultuname')]) {
        sh '''
            vaultpasswordfile="vault_pass.txt"
            echo $vaultpass >> $vaultpasswordfile
            ip=$(cat vmpropsIpAddress.txt)
            node_name=$(cat vmpropsVMNAME.txt | tr -d "\r")
            export ANSIBLE_HOST_KEY_CHECKING=False
            export ANSIBLE_ROLES_PATH=ansible/roles
            ansible-playbook -i ansible/hosts ansible/playbooks/${playBook} --vault-password-file vault_pass.txt
            rm -f $vaultpasswordfile
        '''
    }    
}
