package com.example.dms.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.dms.R
import com.google.android.material.button.MaterialButton
import androidx.appcompat.app.AlertDialog

class ResidenceFragment : Fragment() {

    private lateinit var selectBuilding: AutoCompleteTextView
    private lateinit var selectFloor: AutoCompleteTextView
    private lateinit var selectRoom: AutoCompleteTextView
    private lateinit var checkInButton: MaterialButton

    // Данные: корпуса → этажи → комнаты
    private val buildingData = mapOf(
        "Корпус A" to mapOf(
            "1" to listOf("101", "102", "103"),
            "2" to listOf("201", "202"),
            "3" to listOf("301", "302", "303")
        ),
        "Корпус B" to mapOf(
            "1" to listOf("101", "102"),
            "2" to listOf("201", "202", "203"),
            "3" to listOf("301"),
            "4" to listOf("401", "402")
        ),
        "Корпус C" to mapOf(
            "1" to listOf("101", "102"),
            "2" to listOf("201")
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_residence, container, false)

        selectBuilding = view.findViewById(R.id.selectBuilding)
        selectFloor = view.findViewById(R.id.selectFloor)
        selectRoom = view.findViewById(R.id.selectRoom)
        checkInButton = view.findViewById(R.id.checkInButton)

        // Изначально кнопка неактивна и серая
        checkInButton.isEnabled = false
        checkInButton.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.grey)

        setupBuildingDropdown()
        setupFloorClick()
        setupRoomClick()
        setupTextWatchers()

        checkInButton.setOnClickListener {
            val building = selectBuilding.text.toString()
            val floor = selectFloor.text.toString()
            val room = selectRoom.text.toString()

            if (building.isEmpty() || floor.isEmpty() || room.isEmpty()) {
                Toast.makeText(requireContext(), "Пожалуйста, выберите все поля", Toast.LENGTH_SHORT).show()
            } else {
                showCheckInDialog(building, floor, room)
            }
        }

        return view
    }

    private fun setupBuildingDropdown() {
        val buildings = buildingData.keys.toList()
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, buildings)
        selectBuilding.setAdapter(adapter)

        selectBuilding.setOnClickListener { selectBuilding.showDropDown() }

        selectBuilding.setOnItemClickListener { _, _, position, _ ->
            val selectedBuilding = buildings[position]
            selectFloor.setText("", false)
            selectRoom.setText("", false)
            setupFloorDropdown(selectedBuilding)
        }
    }

    private fun setupFloorClick() {
        selectFloor.setOnClickListener { selectFloor.showDropDown() }
    }

    private fun setupRoomClick() {
        selectRoom.setOnClickListener { selectRoom.showDropDown() }
    }

    private fun setupFloorDropdown(building: String) {
        val floors = buildingData[building]?.keys?.toList() ?: emptyList()
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, floors)
        selectFloor.setAdapter(adapter)

        selectFloor.setOnItemClickListener { _, _, position, _ ->
            val selectedFloor = floors[position]
            selectRoom.setText("", false)
            setupRoomDropdown(building, selectedFloor)
        }
    }

    private fun setupRoomDropdown(building: String, floor: String) {
        val rooms = buildingData[building]?.get(floor) ?: emptyList()
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, rooms)
        selectRoom.setAdapter(adapter)
    }

    private fun setupTextWatchers() {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateCheckInButtonState()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        selectBuilding.addTextChangedListener(watcher)
        selectFloor.addTextChangedListener(watcher)
        selectRoom.addTextChangedListener(watcher)
    }

    private fun updateCheckInButtonState() {
        val isFilled = selectBuilding.text.isNotEmpty() &&
                selectFloor.text.isNotEmpty() &&
                selectRoom.text.isNotEmpty()

        checkInButton.isEnabled = isFilled
        checkInButton.backgroundTintList = if (isFilled) {
            ContextCompat.getColorStateList(requireContext(), R.color.purple) // фиолетовый
        } else {
            ContextCompat.getColorStateList(requireContext(), R.color.grey) // серый
        }
    }

    private fun showCheckInDialog(building: String, floor: String, room: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Подтверждение заселения")
            .setMessage("Вы уверены, что хотите заселиться в $building, этаж $floor, комната $room?")
            .setPositiveButton("Да") { dialog, _ ->
                Toast.makeText(requireContext(), "Заселение в $building, этаж $floor, комната $room выполнено", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
