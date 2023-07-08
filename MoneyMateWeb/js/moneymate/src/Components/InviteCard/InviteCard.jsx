import React, {useContext, useEffect, useState} from 'react';
import './InviteCard.css';
import {RiPencilFill} from "react-icons/ri";
import {AiOutlineCheck, RxCross2, RxDividerHorizontal} from "react-icons/all.js";
import InviteService from "../../Services/InviteService.jsx";


function InviteCard({invite, fetchInvites, selectedType}) {
    const [newStatus, setNewStatus] = useState("")
    async function updateInviteStatus() {
        try {
            const response = await InviteService.updateStatus(invite.id, newStatus);
            console.log(response);
            await fetchInvites()
        } catch (error) {
            console.error('Error updating invite Status:', error);
        }
    }

    function onUpdateInviteStatus (status){
        setNewStatus(status)
        //updateInviteStatus()
    }

    function renderText(invite) {
        if (invite.state === "ACCEPTED") {
            return (
                <button className="accepted-status-message" onClick={() => {}}>
                    Accepted
                </button>
            );
        } else if (invite.state === "REJECTED") {
            return (
                <button className="declined-status-message" onClick={() => {}}>
                    Declined
                </button>
            );
        } else {
            return null;
        }
    }

    function renderButtons(selectedType) {
        if (selectedType === "received") {
            return (
                <>
                    <button className="accept-button-invite" onClick={onUpdateInviteStatus("ACCEPTED")}>
                        <AiOutlineCheck/>
                    </button>
                    <button className="decline-button-invite" onClick={onUpdateInviteStatus("REJECTED")}>
                        <RxCross2/>
                    </button>
                </>
            );
        } else if (selectedType === "send") {
            return (
                <button className="pending-status-message" onClick={() => {}}>
                    Pending
                </button>
            );
        } else {
            return null;
        }
    }

    function renderTitleText(invite){
        if (selectedType === "received") {
            return (<div className="invite-name"> Invite Sent By {invite.sender.username}</div>)
        } else if (selectedType === "send") {
            return (<div className="invite-name"> Invite Sent TO {invite.receiver.username}</div>)
        } else {
            return null;
        }
    }

    return (

        <div className="invite-container">
            <div>
                <div className="invite-header">
                    {renderTitleText(invite)}
                </div>
                <div>
                    <div className="invite-date">{invite.createdAt}</div>
                    <div className="invite-text"> Message: Join Wallet {invite.sharedWallet.name} ?</div>
                </div>

                <div>
                    {invite.onFinished ? renderText(invite) : renderButtons(selectedType)}

                </div>
        </div>

    </div>



    )
}

export default InviteCard;
