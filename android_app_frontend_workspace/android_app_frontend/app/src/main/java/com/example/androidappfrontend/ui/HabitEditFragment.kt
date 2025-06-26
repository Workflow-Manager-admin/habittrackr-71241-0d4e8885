package com.example.androidappfrontend.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.androidappfrontend.R
import com.example.androidappfrontend.model.Habit
import com.example.androidappfrontend.storage.HabitStorage
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment for creating or editing a habit.
 */
class HabitEditFragment : Fragment() {
    private var existingHabit: Habit? = null
    private var habitIndex: Int = -1

    companion object {
        private const val ARG_HABIT = "arg_habit"
        private const val ARG_POSITION = "arg_position"

        fun newInstance(habit: Habit?, position: Int): HabitEditFragment {
            val fragment = HabitEditFragment()
            val b = Bundle()
            b.putSerializable(ARG_HABIT, habit)
            b.putInt(ARG_POSITION, position)
            fragment.arguments = b
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            existingHabit = it.getSerializable(ARG_HABIT) as? Habit
            habitIndex = it.getInt(ARG_POSITION, -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val nameInput = view.findViewById<EditText>(R.id.input_habit_name)
        val descInput = view.findViewById<EditText>(R.id.input_habit_desc)
        val saveBtn = view.findViewById<Button>(R.id.btn_save_habit)
        val cancelBtn = view.findViewById<Button>(R.id.btn_cancel)

        if (existingHabit != null) {
            nameInput.setText(existingHabit?.name)
            descInput.setText(existingHabit?.description)
        }

        saveBtn.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val desc = descInput.text.toString().trim()

            if (name.isEmpty()) {
                Snackbar.make(view, "Please enter the habit name", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val habits = HabitStorage.loadHabits(requireContext())
            if (habitIndex >= 0 && habitIndex < habits.size) { // Update
                habits[habitIndex].name = name
                habits[habitIndex].description = desc
                HabitStorage.saveHabits(requireContext(), habits)
                Snackbar.make(view, "Habit updated", Snackbar.LENGTH_SHORT).show()
            } else { // Create new
                habits.add(Habit(name, desc))
                HabitStorage.saveHabits(requireContext(), habits)
                Snackbar.make(view, "Habit created", Snackbar.LENGTH_SHORT).show()
            }
            // Ensure fragment list properly refreshes after edit/create
            parentFragmentManager.popBackStackImmediate()
        }
        cancelBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
