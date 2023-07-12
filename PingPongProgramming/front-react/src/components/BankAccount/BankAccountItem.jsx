import React from 'react'
import '../styles/BankAccountsStyle.css'
import '../styles/ButtonStyle.css'
import {Auth} from "../../Auth";


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
        <div className={"BankAccountItem Space-Between"}>
            <label>[{props.item["id"]}] {props.item["name"]}</label>
            <div>
                <div className="BankAccount-Balance">
                    {props.item["balance"]}€ 
                </div>
                <button className={"StandardButton Delete-Button"} onClick={() => fetchDelete()}>--</button>
            </div>
        </div>
    );
}