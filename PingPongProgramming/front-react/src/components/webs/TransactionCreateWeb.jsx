import React from 'react'
import '../styles/FormStyle.css'
import {Auth} from "../../Auth";

export class TransactionCreateWeb extends React.Component {
    constructor() {
        super();

        this.submit = this.submit.bind(this);
        this.fetchCreateTransaction = this.fetchCreateTransaction.bind(this);
    }

    async fetchCreateTransaction(data) {
        const response = await fetch("/api/create/transaction", {
            method: 'post',
            headers:{
                'Authentication' : Auth.GetAuth(),
                'Content-Type' : 'application/json'
            },
            body: JSON.stringify(data)
        });
        const dd = await response.json();
    }

    submit(e) {
        e.preventDefault();
        const data = {
            receiverID: e.target.receiverID.value,
            senderID: e.target.senderID.value,
            balance: e.target.balance.value
        };

        this.fetchCreateTransaction(data);

        console.log(data);
    }
    render() {
        return (
            <div className={"TransactionCreateWeb BaseFormBox ShadowBox"}>
                <h3>Create transaction</h3>
                <div className={"HSeparator"}></div>
                <form className={"BaseForm"}>
                    <div className={"FormGroup"}><label>Sender ID</label><input type={"number"} name={"senderID"} placeholder={"Sender ID"}/></div>
                    <div className={"FormGroup"}><label>Receiver ID</label><input type={"number"} name={"receiverID"} placeholder={"Receiver ID"}/></div>
                    <div className={"FormGroup"}><label>Balance</label><input type={"number"} name={"balance"} placeholder={"0.0"}/></div>
                    <input className="StandardFormSubmit" type={"submit"} value={"Create"}/>
                </form>
            </div>
        )
    }
}