import React from 'react';
import './SelectorBox.css';


function InviteTypeSelector({handleInviteTypeChanged, selectedInviteType}) {

    const received = "received"
    const send = "send"
    const handleClick = (e) => { }

    return (
        <div className="wallet-selector">
            <div className="radio-inputs">
                    <label
                        key={received}
                        className={`radio ${selectedInviteType === received ? 'selected' : ''}`}
                    >
                        <input
                            type="radio"
                            name="received"
                            value={received}
                            checked={selectedInviteType === received}
                            onChange={() => handleInviteTypeChanged(received)}
                            onClick={handleClick}
                        />
                        <span className="name">Received</span>
                    </label>

                    <label
                        key={send}
                        className={`radio ${selectedInviteType === send ? 'selected' : ''}`}
                    >
                        <input
                            type="radio"
                            name="send"
                            value={send}
                            checked={selectedInviteType === send}
                            onChange={() => handleInviteTypeChanged(send)}
                            onClick={handleClick}
                        />
                        <span className="name">Sent</span>
                    </label>
            </div>

        </div>
    );
}

export default InviteTypeSelector
