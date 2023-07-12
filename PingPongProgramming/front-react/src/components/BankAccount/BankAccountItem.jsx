import React from 'react'
import '../styles/BankAccountsStyle.css'
import '../styles/ButtonStyle.css'


export const BankAccountItem = (props) => {

    const fetchDelete = async() => {
        const response = await fetch("/api/bankaccounts/" + props.item["id"], {
            method:"delete"
        });
    }

    return (
        <div className={"BankAccountItem Space-Between"}>
            <label>[{props.item["id"]}] {props.item["name"]}</label>
            <div>
                <div className="BankAccount-Balance">
                    {props.item["balance"]}€ 
                </div>
                <button className={"StandardButton Delete-Button"} onClick={() => fetchDelete}>--</button>
            </div>
        </div>
    );
}