### Da Fare per tutte le tabelle del DB
```
CREATE POLICY "Select own user" ON public.users
FOR SELECT
USING (id = (select auth.uid()));
```