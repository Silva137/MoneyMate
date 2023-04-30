import React from 'react';

function Test() {
    return (
        <div className="flip-card">
            <div className="flip-card-inner">
                <div className="flip-card-front">
                    <p className="heading_8264">MASTERCARD</p>
                    <svg viewBox="0 0 48 48" height="36" width="36" y="0px" x="0px" xmlns="http://www.w3.org/2000/svg"
                         className="logo">
                        <path d="M32 10A14 14 0 1 0 32 38A14 14 0 1 0 32 10Z" fill="#ff9800"></path>
                        <path d="M16 10A14 14 0 1 0 16 38A14 14 0 1 0 16 10Z" fill="#d50000"></path>
                        <path
                            d="M18,24c0,4.755,2.376,8.95,6,11.48c3.624-2.53,6-6.725,6-11.48s-2.376-8.95-6-11.48 C20.376,15.05,18,19.245,18,24z"
                            fill="#ff3d00"></path>
                    </svg>


                    <p className="number">9759 2484 5269 6576</p>
                    <p className="valid_thru">VALID THRU</p>
                    <p className="date_8264">1 2 / 2 4</p>
                    <p className="name">BRUCE WAYNE</p>
                </div>
                <div className="flip-card-back">
                    <div className="strip"></div>
                    <div className="mstrip"></div>
                    <div className="sstrip">
                        <p className="code">***</p>
                    </div>
                </div>
            </div>
        </div>
  );
}

export default Test;