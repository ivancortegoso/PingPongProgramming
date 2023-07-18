import React from 'react'
import '../styles/BankAccountsStyle.css'
import '../styles/ButtonStyle.css'
import {Auth} from "../../Auth";
import Popup from 'reactjs-popup';
import { TrashSvg } from '../svg-imgs/TrashSvg';


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
            props.onDeleted(); // TODO THIS IS BULLSHIT
        }
    }

    return (
        <tr className={"Default-Row-Table"}>
            <td>{props.item["id"]}</td>
            <td>{props.item["name"]}</td>
            <td>{props.item["balance"]}€ </td>
            <td>
                <Popup trigger={<button className="NoStyleButton Delete-Button"><TrashSvg height="1em"/></button>} modal nested>
                    <div className="BaseFormBox">
                        <div>Do you want to delete?</div>
                        <button onClick={() => fetchDelete()}>Delete</button>
                    </div>
                </Popup>
            </td>
        </tr>
    );
}