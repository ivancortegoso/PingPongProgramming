import React from 'react'
import '../styles/TransactionsStyle.css'
import '../styles/TableStyle.css'
import {TransactionItem} from "./TransactionItem";
import {Auth} from "../../Auth";
import {useState, useEffect} from 'react'
import { TransactionCreateWeb } from './TransactionCreateWeb';
import Popup from 'reactjs-popup';



export const TransactionsWeb = () => {
    const [transactionList, setTransactionList] = useState([]);
    const [page, setPage] = useState(0);
    const [filter, setFilter] = useState("all");
    const numberItemsPerPage = 18;

    const fetchTransactions = async () => {
        const response = await fetch("http://localhost:8080/api/transaction/" + filter, {
            headers: {
                'Authorization' : Auth.GetAuth()
            }
        });
        const transList = await response.json();
        setTransactionList(transList);
    }

    useEffect(() => {fetchTransactions()}, [filter]);

    const prevPage = () => {
        let old = page;
        if(old > 0)
            old = old - 1;
        setPage(old);
    }

    const nextPage = () => {
        let old = page;
        if(old < Math.floor(transactionList.length / numberItemsPerPage))
            old = old + 1;
        setPage(old);
    }

    const onFilterChange = (e) => {
        setFilter(e.target.value);
    }

    let sublist = transactionList.slice(page*numberItemsPerPage, page*numberItemsPerPage + numberItemsPerPage);

    return (
        <div className={"TransactionsWeb"}>
            <div className={"TransactionsWeb-List ShadowBox"}>
                <div>
                    <Popup trigger ={<button className='Menu-create-transaction StandardButton'>Create</button>} onClose={fetchTransactions} modal nested>
                        {close => (<TransactionCreateWeb onClose={close}/>)}
                    </Popup>
                    <h3>Transactions</h3>
                </div>
                <select onChange={onFilterChange}>
                    <option value="all">all</option>
                    <option value="friends">friends</option>
                    <option value="user">mine</option>
                </select>
                
                <table className="Default-Table">
                    <thead>
                        <tr>
                            <th>Sender</th>
                            <th>Receiver</th>
                            <th>Likes</th>
                            <th>Comments</th>
                            <th className="Balance-Cell">Amount</th>
                        </tr>
                    </thead>
                    <tbody>
                        { sublist.length > 0 &&
                            sublist.map((item, index) => {
                                return(
                                    <React.Fragment key={index}>
                                        <TransactionItem item={item}/>
                                    </React.Fragment>
                                )
                            })
                        }
                    </tbody>
                    
                </table>
                
                {sublist.length === 0 && <div>Empty</div>}
                { transactionList.length > numberItemsPerPage &&
                    (<div>
                        <button onClick={prevPage}>{"<<"}</button>
                        <label> {page} </label>
                        <button onClick={nextPage}>{">>"}</button>
                    </div>)
                }

            </div>
        </div>
    )
    
}

/*export class TransactionsWeb extends React.Component {

    constructor() {
        super();
        this.state = {
            transactionList: [],
        }
        this.fetchTransactions = this.fetchTransactions.bind(this)
    }

    async fetchTransactions() {
        const response = await fetch("http://localhost:8080/api/transaction/" + filter, {
            headers: {
                'Authorization' : Auth.GetAuth()
            }
        });
        const transList = await response.json();
        this.setState({transactionList: transList});
    }

    componentDidMount() {
        this.fetchTransactions()
    }

    render() {
        return (
            <div className={"TransactionsWeb"}>
                <div className={"TransactionsWeb-List ShadowBox"}>
                    Transactions
                    { this.state.transactionList.length > 0 &&
                        this.state.transactionList.map((item, index) => {
                            return(
                                <>
                                    {index == 0 ? "" : <div className={"HSeparator"}></div>}
                                    <TransactionItem item={item}/>
                                </>
                            )
                        })
                    }

                </div>
            </div>
        )
    }
}*/