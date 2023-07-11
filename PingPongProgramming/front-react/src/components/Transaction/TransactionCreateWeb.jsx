import React from 'react'
import '../styles/TransactionsStyle.css'
import {Auth} from "../../Auth";
import { useNavigate } from 'react-router-dom';


export const TransactionCreateWeb = () => {
    let navigate = useNavigate();

    const fetchCreateTransaction = async(data) => {
        const response = await fetch("http://localhost:8080/api/user/create/transaction", {
            method: 'post',
            headers:{
                'Authorization' : Auth.GetAuth(),
                'Content-Type' : 'application/json'
            },
            body: JSON.stringify(data)
        });
        try {
            const dd = await response.json();
        } catch(e) {

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

        close();
    }

    const close = () => {
        navigate(-1);
    }

    return (
        <div className={"TransactionCreateWeb BaseFormBox ShadowBox"}>
            <button className="TransactionCreateWeb-Close" onClick={close}>X</button>
            <h3>Create transaction</h3>
            <div className={"HSeparator"}></div>
            <form className={"BaseForm"} onSubmit={submit}>
                <div className={"FormGroup"}><label>Sender ID</label><input type={"number"} name={"senderID"} placeholder={"Sender ID"}/></div>
                <div className={"FormGroup"}><label>Receiver ID</label><input type={"number"} name={"receiverID"} placeholder={"Receiver ID"}/></div>
                <div className={"FormGroup"}><label>Balance</label><input type={"number"} name={"balance"} placeholder={"0.0"}/></div>
                <input className="StandardFormSubmit" type={"submit"} value={"Create"}/>
            </form>
        </div>
    )
}

/*export class TransactionCreateWeb extends React.Component {
    constructor() {
        super();

        this.submit = this.submit.bind(this);
        this.fetchCreateTransaction = this.fetchCreateTransaction.bind(this);
    }

    async fetchCreateTransaction(data) {
        const response = await fetch("http://localhost:8080/api/user/create/transaction", {
            method: 'post',
            headers:{
                'Authorization' : Auth.GetAuth(),
                'Content-Type' : 'application/json'
            },
            body: JSON.stringify(data)
        });
        try {
            const dd = await response.json();
        } catch(e) {

        }
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
                <form className={"BaseForm"} onSubmit={this.submit}>
                    <div className={"FormGroup"}><label>Sender ID</label><input type={"number"} name={"senderID"} placeholder={"Sender ID"}/></div>
                    <div className={"FormGroup"}><label>Receiver ID</label><input type={"number"} name={"receiverID"} placeholder={"Receiver ID"}/></div>
                    <div className={"FormGroup"}><label>Balance</label><input type={"number"} name={"balance"} placeholder={"0.0"}/></div>
                    <input className="StandardFormSubmit" type={"submit"} value={"Create"}/>
                </form>
            </div>
        )
    }
}*/