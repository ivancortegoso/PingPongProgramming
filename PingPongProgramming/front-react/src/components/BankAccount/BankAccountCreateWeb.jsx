import React from 'react'
import {Auth} from "../../Auth";
import '../styles/BankAccountsStyle.css'
import { useNavigate } from 'react-router-dom';


export const BankAccountCreateWeb = (props) => {
    const fetchCreateBankAccount = async (data) => {
        const response = await fetch("http://localhost:8080/api/user/create/bankaccount", {
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
            name: e.target.name.value,
            balance: e.target.balance.value
        };

        fetchCreateBankAccount(data);

        props.onClose();
        console.log(data);
    }


    return (
        <div className={"BaseFormBox ShadowBox"}>
        <button className="BankAccountCreateWeb-Close" onClick={props.onClose}>X</button>
            <h3>Create bank account</h3>
            <div className={"HSeparator"}></div>
            <form className={"BaseForm"} onSubmit={submit}>
                <div className={"FormGroup"}><label>Name</label><input type={"text"} name={"name"} placeholder={"Name"}/></div>
                <div className={"FormGroup"}><label>Account balance</label><input type={"number"} name={"balance"} placeholder={"0.0"}/></div>
                <input className={"StandardFormSubmit"} type={"submit"} value={"Create"}/>
            </form>

        </div>
    );
}

/*export class BankAccountCreateWeb extends React.Component {
    constructor() {
        super();

        this.submit = this.submit.bind(this);
        this.fetchCreateBankAccount = this.fetchCreateBankAccount.bind(this);
    }

    async fetchCreateBankAccount(data) {
        const response = await fetch("http://localhost:8080/api/user/create/bankaccount", {
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
            name: e.target.name.value,
            balance: e.target.balance.value
        };

        this.fetchCreateBankAccount(data);

        console.log(data);
    }

    render() {
        return (
            <div className={"BankAccountCreateWeb BaseFormBox ShadowBox"}>
                <h3>Create bank account</h3>
                <div className={"HSeparator"}></div>
                <form className={"BaseForm"} onSubmit={this.submit}>
                    <div className={"FormGroup"}><label>Name</label><input type={"text"} name={"name"} placeholder={"Name"}/></div>
                    <div className={"FormGroup"}><label>Account balance</label><input type={"number"} name={"balance"} placeholder={"0.0"}/></div>
                    <input className={"StandardFormSubmit"} type={"submit"} value={"Create"}/>
                </form>

            </div>
        )
    }
}*/