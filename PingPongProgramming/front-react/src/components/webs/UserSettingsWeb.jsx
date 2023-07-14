import React, { useState } from 'react'
import '../styles/UserSettingsStyle.css'
import Popup from 'reactjs-popup'


export const UserSettingsWeb = () => {
    const [friendList, setFriendList] = useState(["Pepe", "Peretti", "Ivanucci", "periquito", "thebest", "random", "hehe", "whatdoyousee"])

    return (
        <div className='UserSettingsWeb ShadowBox'>
            <div className="AccountInfoBox">
                <label>ACCOUNT INFO</label>
                <form>
                    <p><label>First Name</label><input type="text"/></p>
                    <p><label>Last Name</label><input type="text"/></p>
                    <p><label>Email</label><input type="text"/></p>
                    <p><label></label><input type="submit" value="Update"/></p>
                </form>
            </div>
            
            <div className="FriendsBox">
                <label>Friends</label>
                <ul>
                    {friendList.map((item) => {
                        return (
                            <li><img src="https://th.bing.com/th/id/OIP._eiPTOPDhIdzMSO6092xdwHaHa?pid=ImgDet&rs=1"/>{item}<button>Delete</button></li>
                        );
                    })}
                </ul>
                
                <Popup trigger={<button>Add Friend</button>} modal nested>
                    <div className="BaseFormBox ShadowBox">
                        ey
                    </div>
                </Popup>
            </div>
        </div>
    )
}