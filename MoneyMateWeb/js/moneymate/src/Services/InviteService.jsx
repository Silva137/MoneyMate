import {authHeader} from "./AuthHeader.jsx";
import instance from "./AxiosInterceptor.jsx";

class InviteService {

    /** PW */
    createInvite(userName, walletId) {
        const form = { receiverUserName: userName }
        return instance.post(`/api/invitations/wallets/${walletId}`, form,{headers: authHeader()})
            .then(response => {
                console.log(response)
                return response.data
            })
    }

    updateStatus(inviteId, newStatus) {
        const form = { state: newStatus }
        return instance.patch(`/api/invitations/${inviteId}`, form,{headers: authHeader()})
            .then(response => {
                console.log(response)
                return response.data
            })
    }

    getInvites(){
        return instance.get("/api/invitations", {headers: authHeader()})
            .then(response => {
                console.log(response)
                return response.data
            })
    }

}

export default new InviteService();