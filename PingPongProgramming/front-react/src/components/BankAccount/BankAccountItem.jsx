import React from 'react'
import '../styles/BankAccountsStyle.css'
import '../styles/ButtonStyle.css'
import {Auth} from "../../Auth";
import Popup from 'reactjs-popup';


export const BankAccountItem = (props) => {

    const fetchDelete = async() => {
        const response = await fetch("http://localhost:8080/api/bankaccount/" + props.item["id"], {
            method:"delete",
            headers:{
                'Authorization' : Auth.GetAuth()
            },
        });
        if(response.ok)
        {
            window.location.reload(); // TODO THIS IS BULLSHIT
        }
    }

    return (
        <tr className={"Default-Row-Table"}>
            <td>{props.item["id"]}</td>
            <td>{props.item["name"]}</td>
            <td>{props.item["balance"]}€ </td>
            <td>
                <Popup trigger={<button className={"StandardButton Delete-Button"}>-</button>} modal nested>
                    <div>
                        Do you want to delete?
                        <button onClick={() => fetchDelete()}>Delete</button>
                    </div>
                </Popup>
            </td>
        </tr>
    );
}