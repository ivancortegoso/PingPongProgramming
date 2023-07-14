import React from 'react'
import {Auth} from "../../Auth";
import '../styles/BankAccountsStyle.css'


export const BankAccountCreateWeb = (props) => {
    const fetchCreateBankAccount = async (data) => {
        const response = await fetch("http://localhost:8080/api/bankaccount", {
            method: 'post',
            headers:{
                'Authorization' : Auth.GetAuth(),
                'Content-Type' : 'application/json'
            },
            body: JSON.stringify(data)
        });
        if(response.ok) {
            props.onAccept();
            props.onClose();
        }
    }

    const submit = (e) => {
        e.preventDefault();
        const data = {
            name: e.target.name.value,
            balance: e.target.balance.value
        };

        fetchCreateBankAccount(data);
    }


    return (
        <div className={"BaseFormBox ShadowBox"}>
        <button className="BankAccountCreateWeb-Close" onClick={props.onClose}>X</button>
            <h3>Create bank account</h3>
            <div className={"HSeparator"}></div>
            <form className={"BaseForm"} onSubmit={submit}>
                <div className={"FormGroup"}><label>Name</label><input type={"text"} name={"name"} placeholder={"Name"}/></div>
                <div className={"FormGroup"}><label>Account balance</label><input type={"number"} name={"balance"} placeholder={"0.0"}/></div>
                <div className={"FormGroup"}><input className={"StandardFormSubmit"} type={"submit"} value={"Create"}/></div>
            </form>

        </div>
    );
}
