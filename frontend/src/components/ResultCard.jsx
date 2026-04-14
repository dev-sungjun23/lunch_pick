import { useEffect, useState } from 'react';
import { decide } from '../api.js';

export default function ResultCard({ teamId }) {
  const [data, setData] = useState(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  const id = Number(teamId);

  useEffect(() => {
    if (Number.isNaN(id)) {
      setLoading(false);
      setError('잘못된 teamId');
      return undefined;
    }

    let cancelled = false;
    setLoading(true);
    setError('');
    setData(null);

    (async () => {
      try {
        const res = await decide(id);
        if (!cancelled) setData(res);
      } catch (err) {
        if (!cancelled) setError(err.message || '결정 실패');
      } finally {
        if (!cancelled) setLoading(false);
      }
    })();

    return () => {
      cancelled = true;
    };
  }, [id]);

  if (Number.isNaN(id)) {
    return <p style={{ color: 'crimson' }}>잘못된 teamId</p>;
  }
  if (loading) return <p>계산 중…</p>;
  if (error) return <p style={{ color: 'crimson' }}>{error}</p>;
  if (!data) return null;

  return (
    <div
      style={{
        marginTop: 16,
        padding: 16,
        borderRadius: 8,
        border: '1px solid #eee',
        background: '#fafafa',
      }}
    >
      <p style={{ margin: '0 0 4px' }}>팀 ID: {data.teamId}</p>
      <p style={{ margin: '0 0 4px' }}>
        오늘의 점심은 <strong>{data.restaurantName}</strong> (식당 ID: {data.restaurantId})
      </p>
      <p style={{ margin: 0 }}>선택 이유: {data.reason}</p>
    </div>
  );
}
