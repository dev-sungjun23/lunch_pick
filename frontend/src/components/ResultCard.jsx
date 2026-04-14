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
    <div>
      <p>팀 ID: {data.teamId}</p>
      <p>
        <strong>{data.restaurantName}</strong> (식당 ID: {data.restaurantId})
      </p>
      <p>사유: {data.reason}</p>
    </div>
  );
}
