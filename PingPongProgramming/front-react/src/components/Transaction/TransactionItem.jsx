import React from 'react'
import '../styles/TransactionsStyle.css'
import { LikeHeartSvg } from '../svg-imgs/Like-Heart-Svg'
import { useNavigate } from 'react-router-dom'


export const TransactionItem = (props) => {
    const navigate = useNavigate();

    const onClick = () => {
        navigate("/transaction/"+ 1, {replace:true});//props.item["id"]);
    }

    return (
        <tr className='Default-Row-Table' onClick={onClick}>
            <td className="Default-Cell">{props.item["bankAccountSenderName"]}</td>
            <td className="Default-Cell">{props.item["bankAccountReceiverName"]}</td>
            <td className="Default-Cell">
                <label>0</label>
                <LikeHeartSvg/>
            </td>
            <td className="Default-Cell">0</td>
            <td className="Balance-Cell">-{props.item["balance"]} €</td>
        </tr>
    )
}