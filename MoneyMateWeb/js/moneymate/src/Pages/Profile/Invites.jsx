import React, {useContext, useEffect, useState} from 'react';
import '../../App.css';
import './Profile.css';
import '../../Components/WalletCard/WalletCard.css'

import InviteService from "../../Services/InviteService.jsx";
import InviteTypeSelector from "../../Components/SelectorBox/InviteTypeSelector.jsx";

import InviteCard from "../../Components/InviteCard/InviteCard.jsx";
import WalletService from "../../Services/WalletService.jsx";

function Invites({loggedUser, username, handleEditButtonClick, image }) {
    const [sendInvites, setSendInvites] = useState([])
    const [receivedInvites, setReceivedInvites] = useState([])
    const [selectedInviteType, setSelectedInviteType ] = useState("received")

    useEffect( () => {
        fetchInvites()
    }, []);

    async function fetchInvites() {
        try {
            const response = await InviteService.getInvites();
            console.log("invites response")
            console.log(response)
            console.log("invites response", response.send);
            console.log("received invites", response.received);

            setSendInvites(response.send.invites)
            setReceivedInvites(response.received.invites)
        } catch (error) {
            console.error('Error fetching invites of user:', error);
        }
    }



    const handleInviteTypeChanged = async (type) => {
        setSelectedInviteType(type)
    }



    return (
        <div className="content-invites-container">
            <h1 className="page-title">Invites</h1>
            <InviteTypeSelector selectedInviteType={selectedInviteType} handleInviteTypeChanged={handleInviteTypeChanged} selectedType={selectedInviteType}/>
            {selectedInviteType === "received" ?
                (receivedInvites.map((invite, index) => (
                    <InviteCard key={index} invite={invite} fetchInvites={fetchInvites} selectedType={selectedInviteType}/>
                ))):

                (sendInvites.map((invite, index) => (
                    <InviteCard key={index} invite={invite} fetchInvites={fetchInvites} selectedType={selectedInviteType}/>
                )))
            }
        </div>
    )
}

export default Invites;
