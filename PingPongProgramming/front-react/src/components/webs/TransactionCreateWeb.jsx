import React from 'react'
import '../styles/FormStyle.css'

export class TransactionCreateWeb extends React.Component {

    render() {
        return (
            <div className={"TransactionCreateWeb"}>
                <label>Create transaction</label>
                <form className={"BaseForm"}>
                    <div className={"FormGroup"}><label>Receiver</label><input type={"text"} name={"receiver"} placeholder={"Receiver"}/></div>
                    <div className={"FormGroup"}><label>Balance</label><input type={"number"} name={"balance"} placeholder={"0.0"}/></div>
                    <input className="StandardFormSubmit" type={"submit"} value={"Create"}/>
                </form>
            </div>
        )
    }
}