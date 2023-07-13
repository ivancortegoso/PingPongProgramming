import React from 'react'
import '../styles/TransactionsStyle.css'
import '../styles/FormStyle.css'
import {Auth} from "../../Auth";

export const TransactionCreateWeb = (props) => {

    const fetchCreateTransaction = async(data) => {
        const response = await fetch("http://localhost:8080/api/user/create/transaction", {
            method: 'post',
            headers:{
                'Authorization' : Auth.GetAuth(),
                'Content-Type' : 'application/json'
            },
            body: JSON.stringify(data)
        });
        if(response.ok) {
            props.onClose();
        }
    }

    const submit = (e) => {
        e.preventDefault();
        const data = {
            receiverID: e.target.receiverID.value,
            senderID: e.target.senderID.value,
            balance: e.target.balance.value
        };

        fetchCreateTransaction(data);
    }

    return (
        <div className={"BaseFormBox ShadowBox"}>
            <button className="TransactionCreateWeb-Close" onClick={props.onClose}>X</button>
            <h3>Create transaction</h3>
            <div className={"HSeparator"}></div>
            <form className={"BaseForm"} onSubmit={submit}>
                <div className={"FormGroup"}><label>Sender ID</label><input type={"number"} name={"senderID"} placeholder={"Sender ID"}/></div>
                <div className={"FormGroup"}><label>Receiver ID</label><input type={"number"} name={"receiverID"} placeholder={"Receiver ID"}/></div>
                <div className={"FormGroup"}><label>Balance</label><input type={"number"} name={"balance"} placeholder={"0.0"}/></div>
                <div className={"FormGroup"}><input className="StandardFormSubmit" type={"submit"} value={"Create"}/></div>
            </form>
        </div>
    )
}
